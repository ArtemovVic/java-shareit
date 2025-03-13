package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private static final String USER_ID_HEADER = "X-Sharer-User-Id";
    private final BookingService bookingService;

    @PostMapping
    public BookingDto createBooking(@RequestHeader(USER_ID_HEADER) Long userId, @Valid @RequestBody BookingDto bookingDto) {
        log.info("==>Creating booking: {}", bookingDto);
        BookingDto booking = bookingService.createBooking(userId, bookingDto);
        log.info("<==Creating booking: {}", booking.getId());
        return booking;
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approveBooking(@RequestHeader(USER_ID_HEADER) Long userId, @PathVariable Long bookingId, @RequestParam boolean approved) {
        log.info("==>Approve booking: {}", bookingId);
        BookingDto approveBooking = bookingService.approveBooking(userId, bookingId, approved);
        log.info("==>Approve booking: {}", approveBooking.getId());
        return approveBooking;
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@RequestHeader(USER_ID_HEADER) Long userId, @PathVariable Long bookingId) {
        return bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping
    public List<BookingDto> getAllBookingsByUser(@RequestHeader(USER_ID_HEADER) Long userId, @RequestParam(defaultValue = "ALL") String state) {
        return bookingService.getAllBookingsByUser(userId, state);
    }
}
