package com.johnteacher.shoppingcart.config;

import com.johnteacher.shoppingcart.repository.CategoryRepository;
import com.johnteacher.shoppingcart.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // tells spring to process this class at startup, allowing autowired for later
public class ShopConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
