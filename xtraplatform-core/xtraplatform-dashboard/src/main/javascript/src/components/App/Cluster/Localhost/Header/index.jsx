import React from 'react';

import { Header } from '@xtraplatform/core';
import getStatusIcon from '../../../../Icon';

const LocalhostHeader = ({ status }) => {
    return (
        <>
            <Header icon={getStatusIcon(status)} label={'Localhost'} title={'Localhost'} />
        </>
    );
};

LocalhostHeader.displayName = 'LocalhostHeader';

export default LocalhostHeader;
