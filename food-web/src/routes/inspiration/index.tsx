import { createFileRoute } from '@tanstack/react-router'

export const Route = createFileRoute('/inspiration/')({
  component: RouteComponent,
})

function RouteComponent() {
  return (
    <div>
      Linker til nyttige ressurser og inspirasjon.
      <br />
      <ul>
        <li>
          <a
            href="https://cookwell.com"
            target="_blank"
            rel="noreferrer noopener"
          >
            cookwell.com
          </a>{' '}
          | oppskrifter og matlagningsteknikker
        </li>
      </ul>
    </div>
  )
}
