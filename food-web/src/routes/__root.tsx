import {Link, Outlet, createRootRoute} from '@tanstack/react-router'
import {TanStackRouterDevtools} from '@tanstack/router-devtools'
// @ts-ignore
import styles from './root.module.css'

import image from "@/assets/oppskrifter_logo.png"


export const Route = createRootRoute({
    component: RootComponent,
})

function RootComponent() {
    return (
        <>
            <div className={styles["logoContainer"]}>
                <img className={styles["logo"]} src={image}/>
            </div>
            <h1>Oppskrifter</h1>
            <div className={styles["header-row-container"]}>
                <div className={styles["leftItems"]}>
                    <Link to="/">Home</Link>{' '}
                    <Link to="/recipes">Oppskrifter</Link>{' '}
                    <Link to="/inspiration">Inspirasjon</Link>
                </div>
                <div className={styles["rightItems"]}>
                    <Link to="/newRecipe">Ny oppskrift</Link>
                </div>
            </div>

            <hr/>
            <Outlet/>
            <TanStackRouterDevtools position="bottom-right"/>
        </>
    )
}
