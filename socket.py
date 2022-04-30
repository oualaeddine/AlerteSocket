#!/usr/bin/env python
# coding: utf-8

# In[1]:

import socket
import asyncio

import websockets

IP = socket.gethostbyname(socket.gethostname())

# create handler for each connection
async def handler(websocket, path):
    await websocket.send("hello")

start_server = websockets.serve(handler, IP, 8000)

asyncio.get_event_loop().run_until_complete(start_server)

asyncio.get_event_loop().run_forever()

# In[ ]:

