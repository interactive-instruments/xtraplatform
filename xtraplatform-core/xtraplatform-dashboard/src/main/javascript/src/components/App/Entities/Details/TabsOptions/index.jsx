import React, { useCallback } from 'react';
import { Tabs } from '@xtraplatform/core';
import Main from '../Main';
import ClusterDetails from '../../../Cluster';

const TabsOption = ({ currentID, healthchecks, selectedChecks }) => {
    const tabs = [
        { id: 'tab1', label: 'Health', component: Main },
        { id: 'tab2', label: 'Cluster', component: ClusterDetails },
    ];

    return (
        <>
            <Tabs
                tabs={tabs}
                tabProps={{
                    currentID,
                    healthchecks,
                    selectedChecks,
                }}
            />
        </>
    );
};

export default TabsOption;
