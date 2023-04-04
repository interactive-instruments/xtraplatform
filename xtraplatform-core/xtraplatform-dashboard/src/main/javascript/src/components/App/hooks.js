import { useState, useEffect } from 'react';

export const useEntities = () => {
    const [entities, setEntities] = useState({ providers: [] });

    useEffect(() => {
        fetch('entities')
            .then((response) => {
                console.log(response.status);
                return response.json();
            })
            .then((data) => {
                console.log(data);
                setEntities(data);
            })
            .catch((error) => console.log(error));
    }, []);

    return entities;
};

export const useChecks = () => {
    const [healthchecks, setHealthchecks] = useState({});

    useEffect(() => {
        fetch('healthcheck')
            .then((response) => {
                console.log(response.status);
                return response.json();
            })
            .then((data) => {
                console.log(data);
                setHealthchecks(data);
            })
            .catch((error) => console.log(error));
    }, []);

    return healthchecks;
};
