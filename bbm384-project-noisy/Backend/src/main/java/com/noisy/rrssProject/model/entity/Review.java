    package com.noisy.rrssProject.model.entity;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.noisy.rrssProject.model.base.EntityBase;
    import jakarta.persistence.*;
    import java.time.LocalDateTime;

    @Entity
    public class Review implements EntityBase {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private Customer customer;

        @ManyToOne
        @JsonBackReference
        private Product product;

        private int rating;

        private String comment;

        private LocalDateTime reviewDate;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public LocalDateTime getReviewDate() {
            return reviewDate;
        }

        public void setReviewDate(LocalDateTime reviewDate) {
            this.reviewDate = reviewDate;
        }
    }
