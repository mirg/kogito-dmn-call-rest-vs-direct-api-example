package org.acme;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InputData {
    int age;
    int visits;
}
