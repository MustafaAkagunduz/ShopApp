# ShopSmart Projesi
 

## Kurulum

1. PostgreSQL'i [bu bağlantıdan](https://www.postgresql.org/download/) indirin ve kurun.
2. `.env` dosyasını oluşturun ve aşağıdaki ayarları yapın(farklı değişken değerleri kullanabilirsiniz):

    ```
    POSTGRES_HOST=localhost
    POSTGRES_PORT=5432
    POSTGRES_USER=postgres
    POSTGRES_PASSWORD=password
    POSTGRES_DB=shopsmart
    ```
3. Versiyon sorunu yaşamadan ilerlemek isterseniz Java'nın `openjdk-21.0.1` versiyonunu kurabilirsiniz.
4. Todos bölümünü ihtiyaca göre güncelleyebilirsiniz.
## Todos

- [x] ~~Uygulama mimarisi çıkarılacak.~~
- [x] ~~Modellerin attributeları belirlenecek.~~
- [x] ~~Modeller için CRUD fonksiyonları yazılacak.~~
- [x] ~~Security için Spring Security eklentisi yapılacak.~~

## Swagger

Uygulamanın API dokümantasyonu için [Swagger UI](https://swagger.io/tools/swagger-ui/) kullanılmıştır. Swagger UI'ye erişmek için [localhost:8080/swagger-ui/index.html](localhost:8080/swagger-ui/index.html) adresini ziyaret edebilirsiniz.
