package com.devon.building.builder;

import lombok.Getter;

@Getter
public class CustomerSearchBuilder {
    private final String fullName;
    private final String phone;
    private final String email;
    private final Long staffId;
    private final String status;

    public CustomerSearchBuilder(Builder builder) {
        this.fullName = builder.fullName;
        this.phone = builder.phone;
        this.email = builder.email;
        this.staffId = builder.staffId;
        this.status = builder.status;
    }

    public static class Builder {
        private String fullName;
        private String phone;
        private String email;
        private Long staffId;
        private String status;

        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setStaffId(Long staffId) {
            this.staffId = staffId;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public CustomerSearchBuilder build() {
            return new CustomerSearchBuilder(this);
        }
    }
}