import React from 'react';

import { Header, StatusIcon } from '@xtraplatform/core';

const getStatusIcon = (status) => {
    let icon;
    switch (status) {
        case 'Active':
        case true:
        case 'Reloading':
            icon = 'ok';
            break;
        case 'Disabled':
            icon = 'disabled';
            break;
        case 'Defective':
        case false:
            icon = 'critical';
            break;
        case 'Loading':
            icon = 'transit';
            break;
        default:
            icon = 'unknown';
    }
    let message;
    switch (status) {
        case 'Active':
        case true:
            message = 'Online';
            break;
        case 'Disabled':
            message = 'Offline';
            break;
        case 'Defective':
        case false:
            message = 'Defective';
            break;
        case 'Loading':
            message = 'Initializing';
            break;
        case 'Reloading':
            message = 'Reloading';
            break;
        default:
            message = 'Unknown';
    }

    const statusIcon = (
        <StatusIcon value={icon} size={'medium'} a11yTitle={message} title={message} />
    );

    return statusIcon;
};
export default getStatusIcon;
