package org.cybexai.dto;

import lombok.Data;

/**
 * @author Dannick Tchatchoua <email: dannickte@gmail.com>
 */
@Data
public class PersonRequest {
    private String uuid;
    private String firstName;
    private String lastName;
    private int age;
    private String address;
}
