package pl.trytek.easytrip.data.converter;

import pl.trytek.easytrip.data.domain.UserRoleEnum;

import javax.persistence.AttributeConverter;

public class UserRoleEnumConverter implements AttributeConverter<UserRoleEnum, String> {
    public UserRoleEnumConverter() {
    }

    public String convertToDatabaseColumn(UserRoleEnum userRoleEnum) {
        return userRoleEnum.getCode();
    }

    public UserRoleEnum convertToEntityAttribute(String code) {
        return UserRoleEnum.parse(code);
    }
}
