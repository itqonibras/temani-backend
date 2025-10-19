# 💬 Simple Chat API

## 📋 What This Does
This is a simple chat system for counseling sessions. Users can send text messages to each other and see message history.

## 🔗 Base URL
```
http://localhost:8080
```

## 🔐 Authentication
All requests need a JWT token. Get one by logging in first.

---

## 🌐 How to Send Messages (Real-time)

### **Step 1: Connect to Chat**
**URL:** `ws://localhost:8080/chat`

**What to do:**
1. Open a WebSocket connection
2. Send your JWT token in the connection
3. You'll get real-time messages instantly

### **Step 2: Send a Message**
**Destination:** `/app/chat.send`

**Message Format:**
```json
{
  "sessionId": "session-123",
  "receiverUsername": "john_counselor", 
  "content": "Hello, I need help"
}
```

**What you need:**
- `sessionId`: Any string to group messages together (like "client-john-counselor-mary")
- `receiverUsername`: Who you're sending the message to
- `content`: Your message text

### **Step 3: Receive Messages**
**Subscribe to:** `/user/queue/messages`

When someone sends you a message, you'll get it instantly.

---

## 📡 How to Get Message History

### **Get All Messages in a Session**
**URL:** `GET /chat-messages/session/{sessionId}`

**Example:**
```http
GET /chat-messages/session/session-123
Authorization: Bearer YOUR_JWT_TOKEN
```

**Response:**
```json
[
  {
    "id": "msg-1",
    "sessionId": "session-123",
    "senderUsername": "client_mary",
    "receiverUsername": "john_counselor",
    "content": "Hello, I need help",
    "timestamp": "2024-01-15T10:30:00"
  },
  {
    "id": "msg-2", 
    "sessionId": "session-123",
    "senderUsername": "john_counselor",
    "receiverUsername": "client_mary",
    "content": "I'm here to help. What's going on?",
    "timestamp": "2024-01-15T10:31:00"
  }
]
```

### **Get Messages You Sent**
**URL:** `GET /chat-messages/sent/{yourUsername}`

**Example:**
```http
GET /chat-messages/sent/client_mary
Authorization: Bearer YOUR_JWT_TOKEN
```

### **Get Messages You Received**
**URL:** `GET /chat-messages/received/{yourUsername}`

**Example:**
```http
GET /chat-messages/received/john_counselor
Authorization: Bearer YOUR_JWT_TOKEN
```

---

## 🧪 Quick Testing Guide

### **1. Get Your Token**
```http
POST /auth/login
Content-Type: application/json

{
  "username": "your_username",
  "password": "your_password"
}
```

Save the token from the response.

### **2. Test with Postman**

#### **A. Create WebSocket Request**
1. New Request → WebSocket
2. URL: `ws://localhost:8080/chat`
3. Headers: `Authorization: Bearer YOUR_TOKEN`

#### **B. Send a Message**
```json
SEND
destination:/app/chat.send
content-type:application/json

{
  "sessionId": "test-session-1",
  "receiverUsername": "other_user",
  "content": "Hello from Postman!"
}
^@
```

#### **C. Get Message History**
```http
GET /chat-messages/session/test-session-1
Authorization: Bearer YOUR_TOKEN
```

### **3. Test with cURL**
```bash
# Get messages
curl -X GET "http://localhost:8080/chat-messages/session/test-session-1" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---


## 🎯 How to Use in Your App

### **For Counseling Sessions:**
1. **Create a session ID** like: `counseling-{clientId}-{counselorId}`
2. **Client sends message:** "I need help with anxiety"
3. **Counselor receives it instantly** via WebSocket
4. **Counselor replies:** "I'm here to help"
5. **Client sees reply instantly**

### **For General Chat:**
1. **Create a session ID** like: `chat-{user1}-{user2}`
2. **Users send messages back and forth**
3. **All messages are saved** and can be retrieved later

---

## 📊 Message Format

### **When You Send:**
```json
{
  "sessionId": "any-string-you-want",
  "receiverUsername": "username-of-receiver",
  "content": "your message text"
}
```

### **When You Receive:**
```json
{
  "id": "unique-message-id",
  "sessionId": "same-as-you-sent",
  "senderUsername": "who-sent-it",
  "receiverUsername": "who-receives-it", 
  "content": "the message text",
  "timestamp": "2024-01-15T10:30:00"
}
```

---

## 🚨 Common Issues

### **"Connection Failed"**
- Make sure your backend is running on port 8080
- Check if your JWT token is valid
- Try reconnecting

### **"Message Not Sent"**
- Check if receiver username is correct
- Make sure you're connected to WebSocket
- Verify your JWT token

### **"Can't Get Messages"**
- Check if session ID exists
- Verify your JWT token
- Make sure you have permission to see those messages

---
