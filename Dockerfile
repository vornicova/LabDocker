# Используем последнюю версию OpenJDK 23
FROM openjdk:23
# Рабочая директория
WORKDIR /app

# Копируем JAR-файл в контейнер
COPY application.jar /app/application.jar

# Открываем порт
EXPOSE 8080

# Запускаем приложение
CMD ["java", "-jar", "application.jar"]


