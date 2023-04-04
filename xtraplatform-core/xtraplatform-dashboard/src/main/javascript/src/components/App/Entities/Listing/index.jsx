import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { Page } from 'grommet';

import { Content } from '@xtraplatform/core';
import Main from './Main';
import ListingHeader from './Header';
import { useEntities } from '../../hooks';

const EntitiesListing = ({ dark, setDark }) => {
    const entities = useEntities();
    return (
        <Page>
            <Content
                header={<ListingHeader dark={dark} setDark={setDark} />}
                main={<Main entities={entities} />}
            />
        </Page>
    );
};

EntitiesListing.propTypes = {};

EntitiesListing.defaultProps = {};

EntitiesListing.displayName = 'EntitiesListing';

export default EntitiesListing;
