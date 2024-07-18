package edu.poly.asm.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("storage")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageProperties {
    private String location;
}
