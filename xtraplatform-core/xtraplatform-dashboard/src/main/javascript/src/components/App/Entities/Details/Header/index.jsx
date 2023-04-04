import React from 'react';
import PropTypes from 'prop-types';
// import { useFassets } from 'feature-u';

import { Box } from 'grommet';
import { Globe } from 'grommet-icons';
import { Header, TaskProgress, AsyncIcon } from '@xtraplatform/core';

const ServiceEditHeader = ({ service, status }) => {
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
