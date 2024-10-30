package dev.futbolmama.msvcustws.db.model;


import java.time.Instant;

public record Customer(Long id, String fName, String mName, String lName, String email, String phone,
                       Instant modifyDate) {
}
