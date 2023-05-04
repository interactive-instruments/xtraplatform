import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { Page } from 'grommet';

// import { Content } from '@xtraplatform/core';
import Content from './Content';
import Main from './Main';
import ListingHeader from './Header';
import { useEntities } from '../../hooks';
import Filter from './Filter';

const EntitiesListing = ({ dark, setDark }) => {
    const entities = useEntities();
    const DATA = entities.providers.map((provider) => {
        return {
            title: provider.id,
            label: 'Provider',
            status:
                provider.status.charAt(0).toUpperCase() +
                provider.status.substring(1).toLowerCase(),
            id: provider.id,
        };
    });

    return (
        <Page>
            <Content
                header={<ListingHeader dark={dark} setDark={setDark} DATA={DATA} />}
                main={<Filter DATA={DATA} />}
            />
        </Page>
    );
};

EntitiesListing.propTypes = {};

EntitiesListing.defaultProps = {};

EntitiesListing.displayName = 'EntitiesListing';

export default EntitiesListing;
