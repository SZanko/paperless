package at.fhtw.swkom.paperless.mapper;

import org.mapstruct.Named;

import java.util.Optional;

public interface OptionalMapStructHelper {
    @Named("integerToOptional")
    default Optional<Integer> integerToOptional(Integer value) {
        return Optional.ofNullable(value);
    }

    @Named("optionalToInteger")
    default Integer optionalToInteger(Optional<Integer> value) {
        return value.orElse(null);
    }

    @Named("stringToOptional")
    default Optional<String> stringToOptional(String value) {
        return Optional.ofNullable(value);
    }

    @Named("optionalToString")
    default String optionalToString(Optional<String> value) {
        return value.orElse(null);
    }
}
