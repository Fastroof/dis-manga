package com.fastroof.imagestorage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @SequenceGenerator(name = "product_image_id_seq_gen", sequenceName = "product_image_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_image_id_seq_gen")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "image_file", length = 1000)
    private String file;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"productId\":" + productId +
                ", \"file\":\"" + file + '\"' +
                '}';
    }
}
