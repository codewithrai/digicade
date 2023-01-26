import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPlayerGameBadge } from '../player-game-badge.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../player-game-badge.test-samples';

import { PlayerGameBadgeService } from './player-game-badge.service';

const requireRestSample: IPlayerGameBadge = {
  ...sampleWithRequiredData,
};

describe('PlayerGameBadge Service', () => {
  let service: PlayerGameBadgeService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlayerGameBadge | IPlayerGameBadge[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PlayerGameBadgeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a PlayerGameBadge', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const playerGameBadge = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(playerGameBadge).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlayerGameBadge', () => {
      const playerGameBadge = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(playerGameBadge).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PlayerGameBadge', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PlayerGameBadge', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PlayerGameBadge', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlayerGameBadgeToCollectionIfMissing', () => {
      it('should add a PlayerGameBadge to an empty array', () => {
        const playerGameBadge: IPlayerGameBadge = sampleWithRequiredData;
        expectedResult = service.addPlayerGameBadgeToCollectionIfMissing([], playerGameBadge);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(playerGameBadge);
      });

      it('should not add a PlayerGameBadge to an array that contains it', () => {
        const playerGameBadge: IPlayerGameBadge = sampleWithRequiredData;
        const playerGameBadgeCollection: IPlayerGameBadge[] = [
          {
            ...playerGameBadge,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlayerGameBadgeToCollectionIfMissing(playerGameBadgeCollection, playerGameBadge);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlayerGameBadge to an array that doesn't contain it", () => {
        const playerGameBadge: IPlayerGameBadge = sampleWithRequiredData;
        const playerGameBadgeCollection: IPlayerGameBadge[] = [sampleWithPartialData];
        expectedResult = service.addPlayerGameBadgeToCollectionIfMissing(playerGameBadgeCollection, playerGameBadge);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(playerGameBadge);
      });

      it('should add only unique PlayerGameBadge to an array', () => {
        const playerGameBadgeArray: IPlayerGameBadge[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const playerGameBadgeCollection: IPlayerGameBadge[] = [sampleWithRequiredData];
        expectedResult = service.addPlayerGameBadgeToCollectionIfMissing(playerGameBadgeCollection, ...playerGameBadgeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const playerGameBadge: IPlayerGameBadge = sampleWithRequiredData;
        const playerGameBadge2: IPlayerGameBadge = sampleWithPartialData;
        expectedResult = service.addPlayerGameBadgeToCollectionIfMissing([], playerGameBadge, playerGameBadge2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(playerGameBadge);
        expect(expectedResult).toContain(playerGameBadge2);
      });

      it('should accept null and undefined values', () => {
        const playerGameBadge: IPlayerGameBadge = sampleWithRequiredData;
        expectedResult = service.addPlayerGameBadgeToCollectionIfMissing([], null, playerGameBadge, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(playerGameBadge);
      });

      it('should return initial array if no PlayerGameBadge is added', () => {
        const playerGameBadgeCollection: IPlayerGameBadge[] = [sampleWithRequiredData];
        expectedResult = service.addPlayerGameBadgeToCollectionIfMissing(playerGameBadgeCollection, undefined, null);
        expect(expectedResult).toEqual(playerGameBadgeCollection);
      });
    });

    describe('comparePlayerGameBadge', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlayerGameBadge(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePlayerGameBadge(entity1, entity2);
        const compareResult2 = service.comparePlayerGameBadge(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePlayerGameBadge(entity1, entity2);
        const compareResult2 = service.comparePlayerGameBadge(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePlayerGameBadge(entity1, entity2);
        const compareResult2 = service.comparePlayerGameBadge(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
