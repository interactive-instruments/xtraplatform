import React from 'react';
import PropTypes from 'prop-types';

import { Globe } from 'grommet-icons';
import { Header } from '@xtraplatform/core';

const ServiceEditHeader = ({ service }) => {
    // const ViewActions = useFassets(serviceViewActions());

    const token = null;

    return (
        <>
            <Header icon={<Globe />} label={service} title={`${service}`} />
        </>
    );
};

ServiceEditHeader.displayName = 'ServiceEditHeader';

ServiceEditHeader.propTypes = {
    compact: PropTypes.bool,
    role: PropTypes.string,
};

export default ServiceEditHeader;
