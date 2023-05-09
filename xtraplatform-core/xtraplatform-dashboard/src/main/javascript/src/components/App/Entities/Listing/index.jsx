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
    const DATA = [];

    for (const key in entities) {
        if (entities.hasOwnProperty(key)) {
            const array = entities[key];
            DATA.push(
                ...array.map((item) => ({
                    title: item.id,
                    label: key,
                    status:
                        item.status.charAt(0).toUpperCase() +
                        item.status.substring(1).toLowerCase(),
                    id: item.id,
                }))
            );
        }
    }

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
