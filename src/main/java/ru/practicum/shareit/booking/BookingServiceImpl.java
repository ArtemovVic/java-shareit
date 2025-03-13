package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.dao.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public BookingDto createBooking(Long userId, BookingDto bookingDto) {
        User booker = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id: " + userId + " not found."));

        Item item = itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new NotFoundException("Item with id=" + bookingDto.getItemId() + " not found."));

        if (!item.getAvailable()) {
            throw new RuntimeException("The item is not available.");
        }

        Booking booking = new Booking();
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        booking.setItem(item);
        booking.setBooker(booker);
        booking.setStatus(Booking.Status.WAITING);

        Booking savedBooking = bookingRepository.save(booking);
        return BookingMapper.toDto(savedBooking);
    }

    @Override
    public BookingDto approveBooking(Long userId, Long bookingId, boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Item with id: " + bookingId + " not found.")
                );
        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new RuntimeException("Only the owner can confirm the booking.");
        }
        if (booking.getStatus() != Booking.Status.WAITING) {
            throw new RuntimeException("booking has already been processed");
        }
        booking.setStatus(approved ? Booking.Status.APPROVED : Booking.Status.REJECTED);
        Booking updatedBooking = bookingRepository.save(booking);
        return BookingMapper.toDto(updatedBooking);
    }

    @Override
    public BookingDto getBookingById(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking with id: " + bookingId + " not found.")
                );
        if (!booking.getBooker().getId().equals(userId) && !booking.getItem().getOwner().getId().equals(userId)) {
            throw new RuntimeException("Access is denied");
        }
        return BookingMapper.toDto(booking);
    }


    @Override
    public List<BookingDto> getAllBookingsByUser(Long userId, String state) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id: " + userId + " not found.н"));;

        List<Booking> bookings;
        LocalDateTime now = LocalDateTime.now();

        bookings = switch (state.toUpperCase()) {
            case "CURRENT" -> bookingRepository.findAllByBookerAndStartBeforeAndEndAfter(user, now, now);
            case "PAST" -> bookingRepository.findAllByBookerAndEndBefore(user, now);
            case "FUTURE" -> bookingRepository.findAllByBookerAndStartAfter(user, now);
            case "WAITING" -> bookingRepository.findAllByBookerAndStatus(user, Booking.Status.WAITING);
            case "REJECTED" -> bookingRepository.findAllByBookerAndStatus(user, Booking.Status.REJECTED);
            default -> bookingRepository.findAllByBookerOrderByStartDesc(user);
        };

        return bookings.stream()
                .map(BookingMapper::toDto)
                .collect(Collectors.toList());
    }
}
