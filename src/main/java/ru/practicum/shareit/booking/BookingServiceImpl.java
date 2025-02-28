package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

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
        User booker = userRepository.getById(userId);
        Item item = itemRepository.getById(bookingDto.getItem().getId());
        if (!item.getAvailable()) {
            throw new RuntimeException("item is unavailable");
        }
        Booking booking = BookingMapper.toEntity(bookingDto);
        booking.setBooker(booker);
        booking.setItem(item);
        booking.setStatus(Booking.Status.WAITING);
        Booking savedBooking = bookingRepository.save(booking);
        return BookingMapper.toDto(savedBooking);
    }

    @Override
    public BookingDto approveBooking(Long userId, Long bookingId, boolean approved) {
        Booking booking = bookingRepository.findById(bookingId);
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
        Booking booking = bookingRepository.findById(bookingId);
        if (!booking.getBooker().getId().equals(userId) && !booking.getItem().getOwner().getId().equals(userId)) {
            throw new RuntimeException("Access is denied");
        }
        return BookingMapper.toDto(booking);
    }

    @Override
    public List<BookingDto> getAllBookingsByUser(Long userId, Booking.Status state) {
        User user = userRepository.getById(userId);

        List<Booking> bookings = bookingRepository.findAllByBookerAndStatus(user, state);

        return bookings.stream()
                .map(BookingMapper::toDto)
                .collect(Collectors.toList());
    }
}
