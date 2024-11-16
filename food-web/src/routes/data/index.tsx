import {createFileRoute} from '@tanstack/react-router'
import {QueryClient, useQuery} from "@tanstack/react-query";

export const Route = createFileRoute('/data/')({
    component: Data,
})

function Data() {
    const {isPending, error, data, isFetching} = useQuery({
            queryKey: ["data"],
            queryFn: async () => {
                const response = await fetch(
                    'http://localhost:8080'
                )
                return await response.json()
            }
        }
    )

    if (isPending) return 'Loading'
    if (error) return 'An error has occured: ' + error
    return (
        <div>{data}</div>
    )
}
