import socket
import sys
import sched, time
import random
from datetime import datetime


def main():
    IP = "localhost"
    port = 5001
    name = sys.argv[1]
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        host_ip = socket.gethostbyname(IP)
    except socket.gaierror:
        print("Host could not be resolved")
        sys.exit()

    s.connect((host_ip, port))
    print("Connected.")

    scheduler = sched.scheduler(time.time, time.sleep)  # Creates a scheduler to schedule communications
    scheduler.enter(1, 1, communicate, (s, scheduler, name,))  # Runs the communicate method every 1s
    scheduler.run()


def communicate(s, scheduler, name):
    data = s.recv(1024).decode()  # Receive Response
    if data == "2\r\n": # \r\n is the end of message code for sockets in python, this just checks status code
        print("Received restart signal")
    elif data == "1\r\n":
        # status is good, do nothing
        pass
    elif data == "0\r\n":
        print("Received yellow flag")
        # Teams, handle the yellow flag here
    elif data == "-1\r\n":
        print("Received red flag")
        # Teams, handle the red flag here
    s.send((get_status() + " " + name + ": " + datetime.now().strftime('%H:%M:%S') + '\r\n').encode())  # Send message
    scheduler.enter(1, 1, communicate, (s, scheduler, name, ))


def get_status():
    # Dummy code: implement in your control loop in call in client code
    # If good, return 1
    # If yellow flag, return 0
    # If red flag, return -1
    rand = random.randrange(0, 100)
    if rand <= 94:
        return "1"
    elif rand <= 97:
        return "0"
    else:
        return "-1"

if __name__ == "__main__":
    main()