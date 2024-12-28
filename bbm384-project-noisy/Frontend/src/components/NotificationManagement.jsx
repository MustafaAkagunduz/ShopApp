import React, { useState, useEffect } from 'react';
import '../css/NotificationManagement.css';

function NotificationManagement() {
  const [notifications, setNotifications] = useState([]);
  const [printNot, setprintNot] = useState([]);

  const removeAllNotifications = () => {
    setprintNot([]);
  };

  const addNotification = (message, type) => {
    const newNotification = { message, type, id: Date.now() };
    setNotifications([...notifications, newNotification]);
  };

  const showNotificationsByType = type => {
    return notifications.filter(notification => notification.type === type);
  };

  const notification_PreProcces = type => {
    removeAllNotifications();
    const filteredNotifications = showNotificationsByType(type);
    setprintNot(filteredNotifications);
  };

  const removeNotification = id => {
    const updatedNotifications = notifications.filter(notification => notification.id !== id);
    setNotifications(updatedNotifications);
    
    const updatedPrintNot = printNot.filter(notification => notification.id !== id);
    setprintNot(updatedPrintNot);
  };

  useEffect(() => {
    const errorList = [
      { id: 1, message: 'Error 1: Something went wrong!', type: 'info' },
      { id: 2, message: 'Error 2: Another error occurred!', type: 'error' },
      { id: 3, message: 'Warning 1: Be careful!', type: 'warning' },
    ];
    setprintNot(errorList);
    setNotifications(errorList);
  }, []);

  return (
    <div className="notification-container">
      <h2 className="notification-title">Notification Management</h2>
      <button onClick={() => notification_PreProcces('info')} className="notification-button">
        Show Info Notification
      </button>
      <button onClick={() => notification_PreProcces('warning')} className="notification-button">
        Show Warning Notification
      </button>
      <button onClick={() => notification_PreProcces('error')} className="notification-button">
        Show Error Notification
      </button>
      <div>
        {printNot.map(notification => (
          <div key={notification.id} className={`notification notification-${notification.type}`}>
            <span>{notification.message}</span>
            <button onClick={() => removeNotification(notification.id)} className="notification-close">Close</button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default NotificationManagement;
