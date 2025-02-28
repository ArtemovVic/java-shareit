package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;

    @Override
    public ItemRequestDto createRequest(Long userId, ItemRequestDto itemRequestDto) {
        User requestor = userRepository.getById(userId);
        ItemRequest request = ItemRequestMapper.toEntity(itemRequestDto);
        request.setRequestor(requestor);
        ItemRequest savedRequest = itemRequestRepository.save(request);
        return ItemRequestMapper.toDto(savedRequest);
    }

    @Override
    public ItemRequestDto getRequestById(Long userId, Long requestId) {
        ItemRequest request = itemRequestRepository.getById(requestId);
        return ItemRequestMapper.toDto(request);
    }

    @Override
    public List<ItemRequestDto> getAllRequestsByUser(Long userId) {
        User requestor = userRepository.getById(userId);
        return itemRequestRepository.findAllByRequestor(requestor).stream()
                .map(ItemRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemRequestDto> getAllRequests(Long userId) {
        return itemRequestRepository.getAll().values().stream()
                .map(ItemRequestMapper::toDto)
                .collect(Collectors.toList());
    }
}
