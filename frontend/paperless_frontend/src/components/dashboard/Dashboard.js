// src/components/Dashboard.js
import React, { useState, useEffect } from 'react';
import './Dashboard.css';
import DocumentActions from "../documentActions/DocumentActions";

export default function Dashboard() {
    const [message, setMessage] = useState('Loading...');

    useEffect(() => {
        fetch('/api/hello-world')
            .then(response => response.json())
            .then(data => setMessage(data.message))
            .catch(err => setMessage('Error fetching message'));
    }, []);

    return (
        <div className="message-box">
            <h1>Dashboard</h1>
            <p>{message}</p>
            <DocumentActions />
        </div>
    );
}
