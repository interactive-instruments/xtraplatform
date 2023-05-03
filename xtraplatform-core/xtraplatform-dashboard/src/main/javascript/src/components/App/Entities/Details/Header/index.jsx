import React from 'react';
import PropTypes from 'prop-types';

import { Header, StatusIcon } from '@xtraplatform/core';

const getStatusIconType = (status) => {
    switch (status) {
        case 'Active':
        case true:
        case 'Reloading':
            return 'ok';
        case 'Disabled':
            return 'disabled';
        case 'Defective':
        case false:
            return 'critical';
        case 'Loading':
            return 'transit';
        default:
            return 'unknown';
    }
};
const getStatusText = (status) => {
    switch (status) {
        case 'Active':
        case true:
            return 'Online';
        case 'Disabled':
            return 'Offline';
        case 'Defective':
        case false:
            return 'Defective';
        case 'Loading':
            return 'Initializing';
        case 'Reloading':
            return 'Reloading';
        default:
            return 'Unknown';
    }
};

const ServiceEditHeader = ({ service, status }) => {
    // const ViewActions = useFassets(serviceViewActions());
    const statusText = getStatusText(status);

    const statusIcon = (
        <StatusIcon
            value={getStatusIconType(status)}
            size={'medium'}
            a11yTitle={statusText}
            title={statusText}
        />
    );

    return (
        <>
            <Header icon={statusIcon} label={service} title={`${service}`} />
        </>
    );
};

ServiceEditHeader.displayName = 'ServiceEditHeader';

ServiceEditHeader.propTypes = {
    compact: PropTypes.bool,
    role: PropTypes.string,
};

export default ServiceEditHeader;
