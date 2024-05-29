FROM openjdk:22 

# Base image with Java runtime

# Copy your application code
COPY /target/*.jar app.jar

# Expose the port your application listens on
EXPOSE 8761  
# Replace with your actual application port

# Run the application
CMD ["java", "-jar", "app.jar"]
