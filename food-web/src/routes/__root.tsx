import {Link, Outlet, createRootRoute} from '@tanstack/react-router'
import {TanStackRouterDevtools} from '@tanstack/router-devtools'

export const Route = createRootRoute({
    component: RootComponent,
})

function RootComponent() {
    return (
        <>
            <h1>Oppskrifter</h1>
            <div className="p-2 flex gap-2 text-lg">
                <Link
                    to="/"
                    activeProps={{
                        className: 'font-bold',
                    }}
                    activeOptions={{exact: true}}
                >
                    Home
                </Link>{' '}
                <Link
                    to="/recipes"
                    activeProps={{
                        className: 'font-bold',
                    }}
                >
                    Oppskrifter
                </Link>
            </div>
            <hr/>
            <Outlet/>
            <TanStackRouterDevtools position="bottom-right"/>
        </>
    )
}
