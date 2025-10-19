# 🛠️ Simple Chat Implementation Guide

## 📋 Overview
This guide shows you how to implement the simple chat features using your existing backend. No complex features - just basic text messaging.

---

## 🎯 What You Already Have

Your backend already supports:
- ✅ WebSocket chat (`ws://localhost:8080/chat`)
- ✅ REST API for message history
- ✅ JWT authentication
- ✅ Session-based messaging

## 🚀 How to Use It

### **1. Basic Chat Flow**

```
User A connects to WebSocket
    ↓
User A sends message to User B
    ↓
Message is saved to database
    ↓
User B receives message instantly
    ↓
Both users can see message history
```

### **2. Session ID Strategy**

For simple chat, use these session ID patterns:

#### **Counseling Chat:**
```
counseling-{clientId}-{counselorId}
Example: counseling-123-456
```

#### **General Chat:**
```
chat-{user1}-{user2}
Example: chat-alice-bob
```

#### **Schedule-based Chat:**
```
schedule-{scheduleId}
Example: schedule-789
```

---

## 💻 Frontend Implementation Examples

### **React/JavaScript Chat Component**

```jsx
import React, { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

function SimpleChat({ sessionId, currentUser, token }) {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const [stompClient, setStompClient] = useState(null);
  const [connected, setConnected] = useState(false);

  // Connect to chat when component mounts
  useEffect(() => {
    const socket = new SockJS('/chat');
    const client = Stomp.over(socket);
    
    client.connect({ 'Authorization': `Bearer ${token}` }, (frame) => {
      console.log('Connected to chat');
      setConnected(true);
      setStompClient(client);
      
      // Listen for new messages
      client.subscribe('/user/queue/messages', (message) => {
        const msg = JSON.parse(message.body);
        setMessages(prev => [...prev, msg]);
      });
      
      // Load message history
      loadMessageHistory();
    });

    return () => {
      if (client) {
        client.disconnect();
      }
    };
  }, [sessionId, token]);

  // Load message history
  const loadMessageHistory = async () => {
    try {
      const response = await fetch(`/chat-messages/session/${sessionId}`, {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      const history = await response.json();
      setMessages(history);
    } catch (error) {
      console.error('Failed to load messages:', error);
    }
  };

  // Send message
  const sendMessage = (e) => {
    e.preventDefault();
    if (!newMessage.trim() || !stompClient) return;

    const message = {
      sessionId: sessionId,
      receiverUsername: getReceiverUsername(), // You need to implement this
      content: newMessage.trim()
    };

    stompClient.send('/app/chat.send', {}, JSON.stringify(message));
    setNewMessage('');
  };

  // Get receiver username (implement based on your logic)
  const getReceiverUsername = () => {
    // Example: if current user is client, send to counselor
    // You'll need to implement this based on your app logic
    return 'counselor_username'; // or get from props/state
  };

  return (
    <div className="chat-container">
      <div className="chat-header">
        <h3>Chat - Session: {sessionId}</h3>
        <span className={connected ? 'connected' : 'disconnected'}>
          {connected ? 'Connected' : 'Disconnected'}
        </span>
      </div>
      
      <div className="messages">
        {messages.map((msg) => (
          <div key={msg.id} className={`message ${msg.senderUsername === currentUser ? 'own' : 'other'}`}>
            <div className="message-content">{msg.content}</div>
            <div className="message-time">{new Date(msg.timestamp).toLocaleTimeString()}</div>
          </div>
        ))}
      </div>
      
      <form onSubmit={sendMessage} className="message-form">
        <input
          type="text"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          placeholder="Type your message..."
          disabled={!connected}
        />
        <button type="submit" disabled={!connected || !newMessage.trim()}>
          Send
        </button>
      </form>
    </div>
  );
}

export default SimpleChat;
```

### **Flutter Chat Widget**

```dart
import 'package:flutter/material.dart';
import 'package:stomp_dart_client/stomp_dart_client.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class SimpleChatWidget extends StatefulWidget {
  final String sessionId;
  final String currentUser;
  final String token;
  final String receiverUsername;

  const SimpleChatWidget({
    Key? key,
    required this.sessionId,
    required this.currentUser,
    required this.token,
    required this.receiverUsername,
  }) : super(key: key);

  @override
  _SimpleChatWidgetState createState() => _SimpleChatWidgetState();
}

class _SimpleChatWidgetState extends State<SimpleChatWidget> {
  late StompClient stompClient;
  List<Map<String, dynamic>> messages = [];
  TextEditingController messageController = TextEditingController();
  bool connected = false;

  @override
  void initState() {
    super.initState();
    connectToChat();
  }

  void connectToChat() {
    stompClient = StompClient(
      config: StompConfig(
        url: 'ws://localhost:8080/chat',
        onConnect: (StompFrame frame) {
          print('Connected to chat');
          setState(() {
            connected = true;
          });
          
          // Listen for messages
          stompClient.subscribe(
            destination: '/user/queue/messages',
            callback: (StompFrame frame) {
              final message = json.decode(frame.body!);
              setState(() {
                messages.add(message);
              });
            },
          );
          
          // Load message history
          loadMessageHistory();
        },
        onWebSocketError: (dynamic error) {
          print('WebSocket error: $error');
          setState(() {
            connected = false;
          });
        },
      ),
    );
    
    stompClient.activate();
  }

  Future<void> loadMessageHistory() async {
    try {
      final response = await http.get(
        Uri.parse('http://localhost:8080/chat-messages/session/${widget.sessionId}'),
        headers: {'Authorization': 'Bearer ${widget.token}'},
      );
      
      if (response.statusCode == 200) {
        final List<dynamic> history = json.decode(response.body);
        setState(() {
          messages = history.cast<Map<String, dynamic>>();
        });
      }
    } catch (e) {
      print('Failed to load messages: $e');
    }
  }

  void sendMessage() {
    final content = messageController.text.trim();
    if (content.isEmpty || !connected) return;

    final message = {
      'sessionId': widget.sessionId,
      'receiverUsername': widget.receiverUsername,
      'content': content,
    };

    stompClient.send(
      destination: '/app/chat.send',
      body: json.encode(message),
    );
    
    messageController.clear();
  }

  @override
  void dispose() {
    stompClient.deactivate();
    messageController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Chat - ${widget.sessionId}'),
        actions: [
          Icon(
            connected ? Icons.circle : Icons.circle_outlined,
            color: connected ? Colors.green : Colors.red,
          ),
          SizedBox(width: 8),
        ],
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              itemCount: messages.length,
              itemBuilder: (context, index) {
                final message = messages[index];
                final isOwn = message['senderUsername'] == widget.currentUser;
                
                return Align(
                  alignment: isOwn ? Alignment.centerRight : Alignment.centerLeft,
                  child: Container(
                    margin: EdgeInsets.all(8),
                    padding: EdgeInsets.all(12),
                    decoration: BoxDecoration(
                      color: isOwn ? Colors.blue : Colors.grey[300],
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          message['content'],
                          style: TextStyle(
                            color: isOwn ? Colors.white : Colors.black,
                          ),
                        ),
                        Text(
                          DateFormat('HH:mm').format(
                            DateTime.parse(message['timestamp']),
                          ),
                          style: TextStyle(
                            color: isOwn ? Colors.white70 : Colors.grey[600],
                            fontSize: 12,
                          ),
                        ),
                      ],
                    ),
                  ),
                );
              },
            ),
          ),
          Container(
            padding: EdgeInsets.all(8),
            child: Row(
              children: [
                Expanded(
                  child: TextField(
                    controller: messageController,
                    decoration: InputDecoration(
                      hintText: 'Type your message...',
                      border: OutlineInputBorder(),
                    ),
                    enabled: connected,
                  ),
                ),
                SizedBox(width: 8),
                ElevatedButton(
                  onPressed: connected ? sendMessage : null,
                  child: Text('Send'),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
```

---

## 🎨 Simple CSS for Web Chat

```css
.chat-container {
  display: flex;
  flex-direction: column;
  height: 500px;
  border: 1px solid #ccc;
  border-radius: 8px;
  overflow: hidden;
}

.chat-header {
  background: #f5f5f5;
  padding: 12px;
  border-bottom: 1px solid #ddd;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.connected {
  color: green;
}

.disconnected {
  color: red;
}

.messages {
  flex: 1;
  padding: 12px;
  overflow-y: auto;
  background: #fff;
}

.message {
  margin-bottom: 12px;
  max-width: 70%;
}

.message.own {
  margin-left: auto;
}

.message.other {
  margin-right: auto;
}

.message-content {
  padding: 8px 12px;
  border-radius: 12px;
  word-wrap: break-word;
}

.message.own .message-content {
  background: #007bff;
  color: white;
}

.message.other .message-content {
  background: #e9ecef;
  color: black;
}

.message-time {
  font-size: 11px;
  color: #666;
  margin-top: 4px;
}

.message-form {
  display: flex;
  padding: 12px;
  border-top: 1px solid #ddd;
  background: #f9f9f9;
}

.message-form input {
  flex: 1;
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 20px;
  outline: none;
}

.message-form button {
  margin-left: 8px;
  padding: 8px 16px;
  background: #007bff;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
}

.message-form button:disabled {
  background: #ccc;
  cursor: not-allowed;
}
```

---

## 🧪 Testing Your Implementation

### **1. Test WebSocket Connection**
```javascript
// Open browser console and run:
const socket = new SockJS('/chat');
const stomp = Stomp.over(socket);
stomp.connect({}, () => console.log('Connected!'));
```

### **2. Test Message Sending**
```javascript
// Send a test message
stomp.send('/app/chat.send', {}, JSON.stringify({
  sessionId: 'test-session',
  receiverUsername: 'test-user',
  content: 'Hello from browser!'
}));
```

### **3. Test Message History**
```bash
curl -X GET "http://localhost:8080/chat-messages/session/test-session" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

## 🎯 Integration Tips

### **1. Session Management**
- Create session IDs when users start chatting
- Store session IDs in your app state
- Use meaningful session names

### **2. User Management**
- Get receiver username from your user management system
- Handle user authentication properly
- Manage user permissions

### **3. Error Handling**
- Handle WebSocket disconnections
- Show connection status to users
- Retry failed connections

### **4. Message Display**
- Show timestamps in user-friendly format
- Distinguish between own and other messages
- Handle long messages properly

---

## ✅ You're Ready!

With this simple implementation, you have:
- ✅ Real-time messaging
- ✅ Message history
- ✅ User authentication
- ✅ Session-based chat
- ✅ Cross-platform support (Web, Mobile)

No complex features, just simple, working chat! 🎉

