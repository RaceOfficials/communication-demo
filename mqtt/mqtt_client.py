import paho.mqtt.client as mqtt
import ssl
import sys
import json

host = "localhost"
port = 1883
topic = "tags" 

def on_connect(client, userdata, flags, rc):
    print(mqtt.connack_string(rc))

# callback triggered by a new Pozyx data packet
def on_message(client, userdata, msg):       
    received_json = json.loads(msg.payload.decode())
    with open('new_coords.json', 'a') as f1:
        if (received_json[0]['tagId'] == "30310"):
            try:
                f1.writelines("x: " + str(received_json[0]['data']['coordinates']['x'] / 1000) + 
                " y: " + str(received_json[0]['data']['coordinates']['y'] / 1000) + 
                " z: " + str(received_json[0]['data']['coordinates']['z'] / 1000))
                f1.write('\n')
            
            except KeyError:
                pass
    with open('new_coords_all.json', 'a') as f:
        if (received_json[0]['tagId'] == "30310"):
            f.writelines(msg.payload.decode())
            f.write('\n')
        print("Positioning update:", msg.payload.decode())

def on_subscribe(client, userdata, mid, granted_qos):
    print("Subscribed to topic!")

client = mqtt.Client()

# set callbacks
client.on_connect = on_connect
client.on_message = on_message
client.on_subscribe = on_subscribe
client.connect(host, port=port)
client.subscribe(topic)

# works blocking, other, non-blocking, clients are available too.
client.loop_forever()
