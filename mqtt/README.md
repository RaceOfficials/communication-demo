## MQTT Information

MQTT is a subscribe-publish architecture. The code provided will subscribe to a topic, "tags" that is maintained by the broker (server) that the positoning system maintains. 
The developer tags we will provide you will publish your data to the topic, which then will be handled by the Positioning System's software, so that when you are subscribed to the topic, you recieve all of the information for all of the tags.

As you can see in `examplepacket.txt`, this packet will be a list of JSON. There's limited information as of now on how each tag will be ordered within the list, or if it will be consistent, but more information about that is to come soon. For now, the example packet is something you can practice parsing to ensure you are able to get your algorithm to recognize the positioning data. 

The other .json files in this repository are example laps taken with an RC car and a tag. 
10-30-movingavg7-fast-bitrate850 is the most recent test, with a single tag connected and a car moving quickly - with about a 22 Hz update rate. 
10-30-movingavg7-allcars-bitrate850 is a car moving at a speed more similar to what the race teams will be moving at, with all tags connected, so a 5.5-6Hz update rate. 
