import React from 'react';
import PropTypes from 'prop-types';

import { Header } from '@xtraplatform/core';
import getStatusIcon from '../../../../Icon';

const ServiceEditHeader = ({ service, status }) => {
    return (
        <>
            <Header icon={getStatusIcon(status)} label={service} title={`${service}`} />
        </>
    );
};

ServiceEditHeader.displayName = 'ServiceEditHeader';

ServiceEditHeader.propTypes = {
    compact: PropTypes.bool,
    role: PropTypes.string,
};

export default ServiceEditHeader;
