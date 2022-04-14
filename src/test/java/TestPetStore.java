import apiClient.HttpClient;
import config.BaseConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojo.Pet;
import pojo.answers.PetAnswer;

public class TestPetStore {

    private final static long id = (int) (Math.random() * 1000);
    private final static Pet pet = Pet.builder().id(id).name("Vasiliy").build();

    /**
     * Добавление нового питомца ----->
     * Описание: проверка работы добавления нового питомца (pet)
     * Шаги воспроизведения:
     * -> Создание объекта класса Pet
     * -> Отправка POST запроса на добавление нового питомца
     * Ожидаемый результат:
     * -> код ответа 200
     */
    @BeforeEach
    void shouldAddPet() {
        Response response = HttpClient.doPostRequest(BaseConfig.PET_URL, pet);
        Assertions.assertEquals(200, response.statusCode());
    }

    /**
     * Получение нового питомца ----->
     * Описание: проверка работы получения питомца (pet)
     * Шаги воспроизведения:
     * -> Создание объекта класса Pet
     * -> Отправка POST запроса на добавление нового питомца
     * -> Отправка GET запроса на получение этого питомца по id
     * Ожидаемый результат:
     * -> код ответа 200
     * -> отправленный питомец эквивалентен полученному питомцу
     */
    @Test
    void shouldGetPet() {
        Response response = HttpClient.doGetRequest(BaseConfig.PET_URL + id);
        Assertions.assertEquals(200, response.statusCode());
        var petFromResponse = HttpClient.convert(response, Pet.class);
        Assertions.assertAll(
                () -> Assertions.assertEquals(pet.getId(), petFromResponse.getId()),
                () -> Assertions.assertEquals(pet.getName(), petFromResponse.getName())
        );
    }

    /**
     * Удаление нового питомца ----->
     * Описание: проверка работы удаления питомца (pet)
     * Шаги воспроизведения:
     * -> Создание объекта класса Pet
     * -> Отправка POST запроса на добавление нового питомца
     * -> Отправка DELETE запроса на удаление этого питомца по id
     * Ожидаемый результат:
     * -> код ответа 200 (Питомец удален)
     * -> ответ валиден схеме JSON
     */
    @Test
    void shouldDeletePet() {
        var answer = HttpClient.convert(HttpClient.doDeleteRequest(BaseConfig.PET_URL + id), PetAnswer.class);
        Assertions.assertAll(
                () -> Assertions.assertEquals(200, answer.getCode()),
                () -> Assertions.assertEquals(String.valueOf(id), answer.getMessage()),
                () -> Assertions.assertEquals("unknown", answer.getType())
        );
    }

    /**
     * Получение удаленного питомца (НЕГАТИВНЫЙ СЦЕНАРИЙ) ----->
     * Описание: проверка работы получения удаленого (не найденного) питомца (pet)
     * Шаги воспроизведения:
     * -> Создание объекта класса Pet
     * -> Отправка POST запроса на добавление нового питомца
     * -> Отправка DELETE запроса на удаление этого питомца по id
     * -> Отправка GET запроса на получение этого питомца по id
     * Ожидаемый результат:
     * -> код ответа 404 (Питомец не найден)
     * -> ответ валиден схеме JSON
     * -> отправленный питомец эквивалентен полученному питомцу
     */
    @Test
    void notShouldGetPet() {
        var response = HttpClient.doDeleteRequest(BaseConfig.PET_URL + id);
        Assertions.assertEquals(200, response.statusCode());

        response = HttpClient.doGetRequest(BaseConfig.PET_URL + id);
        Assertions.assertEquals(404, response.statusCode());

        var answer = HttpClient.convert(response, PetAnswer.class);
        Assertions.assertAll(
                () -> Assertions.assertEquals("Pet not found", answer.getMessage()),
                () -> Assertions.assertEquals("error", answer.getType())
        );
    }

    /**
     * Удаление питомца дважды (НЕГАТИВНЫЙ СЦЕНАРИЙ)----->
     * Описание: проверка работы удаления питомца (pet)
     * Шаги воспроизведения:
     * -> Создание объекта класса Pet
     * -> Отправка POST запроса на добавление нового питомца
     * -> Отправка DELETE запроса на удаление этого питомца по id
     * -> Повторная отправка DELETE запроса на на удаление этого питомца по id
     * Ожидаемый результат:
     * -> код ответа 404 (Питомец не найден)
     * -> отправленный питомец эквивалентен полученному питомцу
     */
    @Test
    void notShouldDeletePetTwice() {
        var response = HttpClient.doDeleteRequest(BaseConfig.PET_URL + id);
        Assertions.assertEquals(200, response.statusCode());

        //Пробуем удалить второй раз
        response = HttpClient.doDeleteRequest(BaseConfig.PET_URL + id);
        Assertions.assertEquals(404, response.statusCode());
    }

    /**
     * Чистим тестовые данные после тестов
     */
    @BeforeAll
    static void clearTrashAfterTests() {
        HttpClient.doDeleteRequest(BaseConfig.PET_URL + id);
    }

}
