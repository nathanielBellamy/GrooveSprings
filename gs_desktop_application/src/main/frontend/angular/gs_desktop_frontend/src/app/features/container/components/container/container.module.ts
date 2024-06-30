import {NgModule} from "@angular/core";
import {AsyncPipe, NgForOf} from "@angular/common";
import {ContainerComponent} from "./container.component";
import {PlaybackModule} from "../../../playback/playback.module";
import {LibraryModule} from "../../../library/components/library/library.module";

@NgModule({
  declarations: [ContainerComponent],
  exports: [ContainerComponent],
  providers: [],
  imports: [
    NgForOf,
    AsyncPipe,
    PlaybackModule,
    LibraryModule
  ],
})
export class ContainerModule {}
