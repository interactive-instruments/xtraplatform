import React, { useState } from 'react';

import { Box, Anchor, Paragraph, Markdown } from 'grommet';
import { Power as PowerIcon, Trash as TrashIcon } from 'grommet-icons';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';

import { LayerConfirm } from '@xtraplatform/core';

const Power = styled(Box)`
    & a {
        &:hover {
            & svg {
                stroke: ${(props) => props.theme.global.colors[props.hoverColor]};
            }
        }
    }
`;

const ServiceActions = ({
    id,
    status,
}) => {
    const [layerOpened, setLayerOpened] = useState(false);
    const [deletePending, setDeletePending] = useState(false);
    const { t } = useTranslation();

    const onLayerOpen = () => setLayerOpened(true);

    const onLayerClose = () => setLayerOpened(false);

    const isOnline = status === 'ACTIVE' || status === 'RELOADING';
    const isLoading = status === 'LOADING';
    const isDefective = status === 'DEFECTIVE';
    // not needed anymore, handled by cookies
    const parameters = ''; // secured ? `?token=${token}` : ''
    

    return (
        <Box flex={false}>
            <Box direction='row' justify='end'>
                <Power
                    key='power'
                    hoverColor={
                        isOnline ? 'status-critical' : isDefective ? 'status-critical'  : 'status-ok'
                    }>
                    <Anchor
                        icon={<PowerIcon />}
                        title={`${
                            isOnline
                                ? t('services/ogc_api:services.stop._label')
                                : isDefective
                                ? 'Defective'
                                : isLoading
                                ? 'Initializing'
                                : t('services/ogc_api:services.start._label')
                        }`}
                        color={
                            isOnline || isLoading
                                ? 'status-ok'
                                : isDefective
                                ? 'status-critical'
                                : 'status-disabled'
                        }
                    />
                </Power>
                <Anchor
                    key='remove'
                    icon={<TrashIcon />}
                    title={t('services/ogc_api:services.delete._label')}
                    onClick={onLayerOpen}
                />
            </Box>
            {layerOpened && (
                <LayerConfirm
                    title={t('services/ogc_api:services.delete.confirm._label')}
                    labelConfirm={t('services/ogc_api:services.delete.confirm.proceed')}
                    colorConfirm='status-critical'
                    labelCancel={t('services/ogc_api:services.delete.confirm.cancel')}
                    colorCancel='brand'
                    isPending={deletePending}
                    compact
                    onClose={onLayerClose}
                    >
                    <Paragraph>
                        <Markdown>
                            {t('services/ogc_api:services.delete.confirm._description', { id })}
                        </Markdown>
                    </Paragraph>
                </LayerConfirm>
            )}
        </Box>
    );
};

ServiceActions.displayName = 'ServiceActions';

export default ServiceActions;
