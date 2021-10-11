## MQTT Information

MQTT is a subscribe-publish architecture. The code provided will subscribe to a topic, "tags" that is maintained by the broker (server) that the positoning system maintains. 
The developer tags we will provide you will publish your data to the topic, which then will be handled by the Positioning System's software, so that when you are subscribed to the topic, you recieve all of the information for all of the tags.

As you can see in `examplepacket.txt`, this packet will be a list of JSON. There's limited information as of now on how each tag will be ordered within the list, or if it will be consistent, but more information about that is to come soon. For now, the example packet is something you can practice parsing to ensure you are able to get your algorithm to recognize the positioning data. 
