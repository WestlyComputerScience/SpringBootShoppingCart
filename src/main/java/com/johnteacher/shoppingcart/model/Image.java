package com.johnteacher.shoppingcart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Normalized;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "image_t")
public class Image {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String fileType;

    @Lob // Specifies that this property/field should be stored in the database as a large object
    private Blob blob; // Blob = Binary Large Object (aka it stores images, audio files, videos, PDFs, etc.)

    private String downloadUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product products;

}
