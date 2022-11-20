package org.acme;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OutputData {
    int age;
    int visits;
    float percent;

}
