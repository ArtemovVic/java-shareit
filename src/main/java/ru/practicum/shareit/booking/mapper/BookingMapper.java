package ru.practicum.shareit.booking.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.user.controller.mappers.UserMapper;

@Component
public class BookingMapper {

    public static BookingDto toDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setItem(ItemMapper.toDto(booking.getItem())); // Полный объект ItemDto
        bookingDto.setBooker(UserMapper.toDto(booking.getBooker()));
        bookingDto.setStatus(booking.getStatus().name());
        return bookingDto;
        /*return new BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem() != null ? ItemMapper.toDto(booking.getItem()) : null,
                booking.getBooker() != null ? UserMapper.toDto(booking.getBooker()) : null,
                booking.getStatus().toString()
        );*/
    }

    public static Booking toEntity(BookingDto bookingDto) {
        return new Booking(
                bookingDto.getId(),
                bookingDto.getStart(),
                bookingDto.getEnd(),
                bookingDto.getItem() != null ? ItemMapper.toEntity(bookingDto.getItem()) : null,
                bookingDto.getBooker() != null ? UserMapper.toEntity(bookingDto.getBooker()) : null,
                Booking.Status.valueOf(bookingDto.getStatus())
        );
    }
}
