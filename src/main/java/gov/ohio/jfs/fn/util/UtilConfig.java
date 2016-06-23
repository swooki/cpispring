package gov.ohio.jfs.fn.util;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {Encrypter.class})
public class UtilConfig {
}
