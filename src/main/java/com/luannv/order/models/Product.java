package com.luannv.order.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String description;
  private String shortDescription;
  private long price;
  private int quantity;
  @OneToMany(mappedBy = "product")
  private List<Review> reviews;
  @ManyToOne
  @JoinColumn(name = "cate_id")
  private Category category;

  @OneToMany(mappedBy = "productDetails", cascade = CascadeType.ALL)
  private List<CartDetail> cartDetails;
  @OneToMany(mappedBy = "productImages", cascade = CascadeType.ALL)
  private List<ProductImage> productImages;
}
