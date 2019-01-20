# hotel-electricity-management

##Spring Boot Application to manage the electricity consumption in a hotel.

Use resources/HotelInput.txt to provide input to this application. First 3 lines defines number of floors, number of main corridors and number of sub corridors respectively.
After that, every 2 lines defines the floor number and sub corridor number on which the motion is detected by the sensor and the light for the same needs to be turned on. I have used this input to validate the various scenario in the application. I read this inputs at an interval of every 10 seconds(hard coded).

Note : I have also provided an api at **{host}/floor/{floorId}/subcorridor/{subcorridorId}** to provide sensor movements.


