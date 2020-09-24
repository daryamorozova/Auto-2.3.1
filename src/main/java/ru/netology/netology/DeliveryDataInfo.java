package ru.netology.netology;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class DeliveryDataInfo {
    private final String city;
    private final String name;
    private final String phone;

}
