package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {
    ItemRequestDto createRequest(Long userId, ItemRequestDto itemRequestDto);

    ItemRequestDto getRequestById(Long userId, Long requestId);

    List<ItemRequestDto> getAllRequestsByUser(Long userId);

    List<ItemRequestDto> getAllRequests(Long userId);
}
