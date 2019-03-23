package model.entity.dto;

import annotation.TableField;
import annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName(name = "user")
public class User implements Serializable {
    @TableField(name = "login", primaryKey = true)
    private String login;
    @TableField(name = "password")
    private String password;
    @TableField(name = "email")
    private String email;
    @TableField(name = "name")
    private String name;
    @TableField(name = "surname")
    private String surname;
    @TableField(name = "admin")
    private boolean admin;

    public User(Builder builder) {
        this.login = builder.login;
        this.password = builder.password;
        this.email = builder.email;
        this.name = builder.name;
        this.surname = builder.surname;
        this.admin = builder.admin;
    }

    public static class Builder {
        private String login;
        private String password;
        private String email;
        private String name;
        private String surname;
        private boolean admin;

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder admin(boolean admin) {
            this.admin = admin;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        User user = (User) object;
        return isAdmin() == user.isAdmin() &&
                Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getSurname(), user.getSurname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword(), getEmail(), getName(), getSurname(), isAdmin());
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", admin=" + admin +
                '}';
    }
}
