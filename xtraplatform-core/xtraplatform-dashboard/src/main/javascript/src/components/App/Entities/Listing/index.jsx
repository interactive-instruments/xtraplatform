import React from 'react';
import PropTypes from 'prop-types';
import { Page } from 'grommet';

// import { Content } from '@xtraplatform/core';
import Content from './Content';
import ListingHeader from './Header';
import { useEntities } from '../../hooks';
import Filter from './Filter';

const EntitiesListing = ({ dark, setDark }) => {
    const entities = useEntities();
    const DATA = entities.providers.map((provider) => {
        return {
            title: provider.id,
            label: 'provider',
            status:
                provider.status.charAt(0).toUpperCase() +
                provider.status.substring(1).toLowerCase(),
            id: provider.id,
        };
    });

    return (
        <Page>
            <Content
                header={<ListingHeader dark={dark} setDark={setDark} />}
                main={<Filter DATA={DATA} />}
            />
        </Page>
    );
};

EntitiesListing.propTypes = {};

EntitiesListing.defaultProps = {};

EntitiesListing.displayName = 'EntitiesListing';

export default EntitiesListing;
