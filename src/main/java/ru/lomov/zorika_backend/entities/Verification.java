package ru.lomov.zorika_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Verification {
    private boolean status = false;
    private LocalDateTime startedAt;
    private LocalDateTime endsAt;
    private String planType;

}
