package ice.spot.dto.global;

import ice.spot.exception.CommonException;
import ice.spot.exception.ErrorCode;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public record ResponseDto<T> (
    @JsonIgnore HttpStatus httpStatus,
    boolean success,
    @Nullable T data,
    @Nullable ExceptionDto exceptionDto
) {
    public static <T> ResponseDto<T> ok(T data){
        return new ResponseDto<>(
                HttpStatus.OK,
                true,
                data,
                null
        );
    }
    public static ResponseDto<Boolean> created(Boolean data){
        return new ResponseDto<>(
                HttpStatus.CREATED,
                true,
                data,
                null
        );
    }
    public static ResponseDto<?> fail(@NotNull CommonException e){
        return new ResponseDto<>(
                e.getErrorCode().getHttpStatus(),
                false,
                null,
                new ExceptionDto(e.getErrorCode())
        );
    }

    public static ResponseDto<?> fail(final MissingServletRequestParameterException e) {
        return new ResponseDto<>(
                HttpStatus.BAD_REQUEST,
                false,
                null,
                new ExceptionDto(ErrorCode.MISSING_REQUEST_PARAMETER)
        );
    }

    public static ResponseDto<?> fail(final MethodArgumentTypeMismatchException e) {
        return new ResponseDto<>(
                HttpStatus.INTERNAL_SERVER_ERROR,
                false,
                null,
                new ExceptionDto(ErrorCode.INVALID_PARAMETER_FORMAT)
        );
    }
}
